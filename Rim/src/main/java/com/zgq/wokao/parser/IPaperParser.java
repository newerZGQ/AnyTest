package com.zgq.wokao.parser;

import java.io.InputStream;
import java.util.ArrayList;

public interface IPaperParser extends IParser {
    ArrayList<PaperParser.Topic> parseTopic(InputStream is);
}
