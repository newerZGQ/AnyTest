package com.zgq.wokao.parser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/18.
 */

public interface IPaperParser extends IParser {
    ArrayList<PaperParser.Topic> parseTopic(InputStream is);
}
