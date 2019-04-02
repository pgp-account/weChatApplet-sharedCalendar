package com.bdilab.sharedcalendar.utils;
import java.util.UUID;
public class UuidGenerator {
    /**
     * UUID 含义是通用唯一识别码 (Universally Unique Identifier)，这是一个软件建构的标准。
     * 也是被开源软件基金会 (Open Software Foundation, OSF) 的组织应用在分布式计算环境 (Distributed Computing Environment, DCE) 领域的一部分。
     * UUID 的目的，是让分布式系统中的所有元素，都能有唯一的辨识资讯，而不需要透过中央控制端来做辨识资讯的指定。
     * 如此一来，每个人都可以建立不与其它人冲突的 UUID。在这样的情况下，就不需考虑数据库建立时的名称重复问题。
     * @return 去除了'-'的32位UUID
     */
    public static String generate(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
