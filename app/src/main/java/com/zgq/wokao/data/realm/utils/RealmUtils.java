package com.zgq.wokao.data.realm.utils;

/**
 * Created by zgq on 2017/4/7.
 */

import android.util.Log;


import java.lang.reflect.Method;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 */

public class RealmUtils
{
    public static void deleteCascade( RealmObject dataObject )
    {
        if (dataObject == null)
        {
            return;
        }
        if( IRealmCascade.class.isAssignableFrom( dataObject.getClass() ) )
        {
            for( Method method : dataObject.getClass().getSuperclass().getDeclaredMethods() )
            {
                try {
                    //Ignore generated methods
                    if( (method.getName().contains("realmGet$")) || (method.getName().contains("access$super")) )
                    {
                        continue;
                    }
                    Class<?> resultType = method.getReturnType();
                    //Ignore non object members
                    if (resultType.isPrimitive()) {
                        continue;
                    }

                    if (RealmObject.class.isAssignableFrom(resultType)) {
                        //Delete Realm object
                        try {
                            RealmObject childObject = (RealmObject) method.invoke(dataObject);
                            RealmUtils.deleteCascade(childObject);
                        } catch (Exception ex) {
                            Log.e("REALM", "CASCADE DELETE OBJECT: " + ex.toString());
                        }
                    } else if (RealmList.class.isAssignableFrom(resultType)) {
                        //Delete RealmList items
                        try {
                            RealmList childList = (RealmList) method.invoke(dataObject);
                            while( childList.iterator().hasNext() )
                            {
                                RealmObject listItem = (RealmObject)childList.iterator().next();
                                RealmUtils.deleteCascade(listItem);
                            }
                        } catch (Exception ex) {
                            Log.e("REALM", "CASCADE DELETE LIST: " + ex.toString());
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.e("REALM", "CASCADE DELETE ITERATION: " + ex.toString());
                }
            }
        }
        dataObject.deleteFromRealm();
    }

}
