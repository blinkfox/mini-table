package com.blinkfox.minitable;

import org.junit.Assert;
import org.junit.Test;

/**
 * MiniTableTest.
 *
 * @author blinkfox on 2019-01-13.
 */
public class MiniTableTest {

    private static final String TABLE = ""
            + "+-------------------------------------------------------------+\n"
            + "|                          The Title                          |\n"
            + "+-----------+--------+-----+--------------------+-------------+\n"
            + "|   Name    |  Sex   | Age |       Email        |    Phone    |\n"
            + "+-----------+--------+-----+--------------------+-------------+\n"
            + "|   LiLei   |  male  | 25  |  lilei@gmail.com   | 13809345219 |\n"
            + "| hanMeiMei | female | 23  |    hmm@163.com     | 13515343853 |\n"
            + "+-----------+--------+-----+--------------------+-------------+\n"
            + "|   Name    |  Sex   | Age |       Email        |    Phone    |\n"
            + "+-----------+--------+-----+--------------------+-------------+\n"
            + "| ZhangSan  | female | 32  | zhangsan@gmail.com | 13920199836 |\n"
            + "|   Lisi    |  male  | 28  |    lisi@qq.com     | 13635781534 |\n"
            + "|  WangWu   |  male  | 48  |   wangwu@163.com   | 15809876236 |\n"
            + "+-----------+--------+-----+--------------------+-------------+\n";

    /**
     * 测试正常渲染的情况.
     */
    @Test
    public void render() {
        String table = new MiniTable("The Title")
                .addHeaders("Name", "Sex", "Age", "Email", "Phone")
                .addDatas("LiLei", "male", 25, "lilei@gmail.com", "13809345219")
                .addDatas("hanMeiMei", "female", 23, "hmm@163.com", "13515343853")
                .addHeaders("Name", "Sex", "Age", "Email", "Phone")
                .addDatas("ZhangSan", "female", 32, "zhangsan@gmail.com", "13920199836")
                .addDatas("Lisi", "male", 28, "lisi@qq.com", "13635781534")
                .addDatas("WangWu", "male", 48, "wangwu@163.com", "15809876236")
                .render();
        Assert.assertEquals(TABLE, table);
    }

    /**
     * 测试参数不对时的情况.
     */
    @Test(expected = IllegalArgumentException.class)
    public void renderWithException() {
        new MiniTable().addHeaders("col11", "col12")
                .addDatas("col21")
                .render();
    }

    /**
     * 测试参数不对时的情况.
     */
    @Test
    public void renderWithLongTitle() {
        String table = new MiniTable("This is a too long title.")
                .addHeaders("col11", "col12")
                .render();
        Assert.assertEquals(""
                + "+---------------+\n"
                + "| This is a too |\n"
                + "+-------+-------+\n"
                + "| col11 | col12 |\n"
                + "+-------+-------+\n", table);
    }

}