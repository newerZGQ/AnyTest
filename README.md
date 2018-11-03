---
title: AnyTest-学生的福音
date: 2018-11-03 20:51:06
tags:
categories: 项目
---

# AnyTest
## 这是什么
这是一个非常有意思的希望能够提高学生或者文字记忆者效率的一款应用。这款app基于MVP模式，使用Realm进行数据存储，使用RxJava完成异步调用，使用lombok减少代码量，使用butterknife进行view绑定，使用dagger2完成依赖注入，可以说这个项目是使用了相对来说比较新潮的技术来开发的，这也是我做这个项目的初衷，就是接触更多的技术。如果你也对这些项目有兴趣，不防在这里集中的了解一下。
## 为什么做
在我还未成为一名coder之前，有一次因为工作原因，需要记忆大量的题目，题目类型都是比较常见的填空题选择题之类，但是因为题库的书不便于随身携带，就萌发写一个app的想法，希望这个app可以解析文档，并将题目内容完整的存储在app上，并且按照题目类别以不同方式进行展示和交互，帮助记忆，这就是AnyTest的由来。
## 有什么特点
* 解析文档
把文档整理成类似[这个文件](https://github.com/newerZGQ/AnyTest/blob/master/Rim/src/main/assets/sample.txt)之后放入sd卡中，打开app点击‘+’号选择该文件即可进行解析。文档解析是基于字符的，对文档格式要求较高。
* 支持题目类型
目前支持填空题、判断题、单选题、多选题以及简答题，每种题目都支持交互学习，支持收藏功能
* 任务管理
可以设置文档是否进入学习状态
每个解析的文档都可以单独设置每日学习量
查看最近一周的学习记录
支持每个文档的答题正确率
## 这个项目用了什么
* MVP模式  
[googlesamples/android-architecture](https://github.com/googlesamples/android-architecture)这个仓库中对google推崇的MVP模式有非常相近的介绍。
* Realm  
[Realm](https://realm.io/)是一个移动端的存储方案，区别于传统sqlite的表的概念，Realm中的增删改查都是针对对象的，也就是说，你查询的结果直接就是一个对象，非常的便捷。Realm中的对象会有多种状态，用于分辨是否可进行修改等操作。另外，Realm还提供了方便的数据库检索app，更加直观的查看当前的数据库内容。
* Dagger2  
[Dagger2](https://google.github.io/dagger/)依赖注入的神器，Dagger2相对于Dagger也有很大提升。
* lombok  
[lombok](https://projectlombok.org/)
自动生成代码的神器，远离get和set
* RxJava  
[RxJava](https://github.com/ReactiveX/RxJava)
* butterknife  
[butterknife](http://jakewharton.github.io/butterknife/)


