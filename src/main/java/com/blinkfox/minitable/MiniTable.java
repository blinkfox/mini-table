package com.blinkfox.minitable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成 ASCII TABLE 的主核心类.
 *
 * @author blinkfox on 2019-01-13.
 */
public final class MiniTable {

    /** 表格标题. */
    private String title;

    /** 最后处理行类型. */
    private RowType lastRowType;

    /** 用于拼接字符串的 StringBuilder 对象. */
    private StringBuilder join;

    /** 用来存放每行数据的有序Map. */
    private List<Row> rows;

    /** 用来存放每列长度最大值的Map. */
    private Map<Integer, Integer> maxColMap;

    /**
     * 默认的空构造方法.
     */
    public MiniTable() {
        this.init();
    }

    /**
     * 包含标题的构造方法.
     *
     * @param title 标题
     */
    public MiniTable(String title) {
        this.init();
        this.title = title;
    }

    /**
     * 初始化数据.
     */
    private void init() {
        this.join = new StringBuilder();
        this.rows = new ArrayList<Row>();
        this.maxColMap = new HashMap<Integer, Integer>();
    }

    /**
     * 将集合中的元素添加到表格中的表头数据中.
     *
     * @param headers 表头数据
     * @return MiniTable对象
     */
    public MiniTable addHeaders(List<?> headers) {
        return this.appendRows(RowType.HEADER, headers.toArray());
    }

    /**
     * 向表格中添加表头数据.
     *
     * @param objects 表头数据
     * @return MiniTable对象
     */
    public MiniTable addHeaders(Object... objects) {
        return this.appendRows(RowType.HEADER, objects);
    }

    /**
     * 向表格中添加一行普通数据.
     *
     * @param datas 普通行数据
     * @return MiniTable对象
     */
    public MiniTable addDatas(List<?> datas) {
        return this.appendRows(RowType.DATA, datas.toArray());
    }

    /**
     * 向表格中添加一行普通数据.
     *
     * @param objects 普通行数据
     * @return MiniTable对象
     */
    public MiniTable addDatas(Object... objects) {
        return this.appendRows(RowType.DATA, objects);
    }

    /**
     * 向表格添加中一行数据.
     *
     * @param objects 表行数据
     * @return MiniTable对象
     */
    private MiniTable appendRows(RowType rowType, Object... objects) {
        int len;
        if (objects != null && (len = objects.length) > 0) {
            if (this.maxColMap.size() > len) {
                throw new IllegalArgumentException("向表格中插入某行数据的列数与之前的列数不同,请检查!");
            }

            // 遍历传入的数据集合，并将其存入到行数据对象中.
            List<String> datas = new ArrayList<String>();
            for (int i = 0; i < len; i++) {
                Object o = objects[i];
                String value = o == null ? "null" : o.toString();
                datas.add(value);

                // 获取第 i 列的最大长度值，如果该列数据不存在，就存储起来.
                Integer maxColSize = this.maxColMap.get(i);
                if (maxColSize == null) {
                    this.maxColMap.put(i, value.length());
                    continue;
                }

                // 如果当前列的长度大于了以前的最大列，就覆盖最大列长度为当前的列长度.
                if (value.length() > maxColSize) {
                    this.maxColMap.put(i, value.length());
                }
            }
            this.rows.add(new Row(rowType, datas));
        }
        return this;
    }

    /**
     * 构建表格标题所在行的字符串.
     *
     * <p>这里重点需要计算出标题所占用的最大字符串长度，然后截取出标题的字符串.
     *     计算标题字符串最大长度的公式: `maxTitleSize = n * (maxColSize) + (n - 1) * 3`;
     * </p>
     */
    private void buildTitle() {
        if (this.title != null) {
            // 计算出标题字符串最大长度.
            int maxTitleSize = 0;
            for (Integer maxColSize : this.maxColMap.values()) {
                maxTitleSize += maxColSize;
            }
            maxTitleSize += 3 * (this.maxColMap.size() - 1);

            // 如果最大的标题字符串长度大于了现有的标题，就对现有的标题进行截取.
            if (this.title.length() > maxTitleSize) {
                this.title = this.title.substring(0, maxTitleSize);
            }

            // 拼接出标题行的上边框和标题行的数据.
            this.join.append("+");
            for (int i = 0; i < maxTitleSize + 2; i++) {
                this.join.append("-");
            }
            this.join.append("+\n")
                    .append("|")
                    .append(StrUtils.center(this.title, maxTitleSize + 2, ' '))
                    .append("|\n");

            this.lastRowType = RowType.TITLE;
        }
    }

    /**
     * 构建生成表格 table，先构建出标题，然后遍历每行数据进行构建.
     */
    private void buildTable() {
        this.buildTitle();

        // 遍历每行的数据，生成表格的行数据.
        for (int i = 0, len = this.rows.size(); i < len; i++) {
            List<String> datas = this.rows.get(i).datas;
            switch (this.rows.get(i).rowType) {
                // 如果当前行是表头，则需要考虑生成上下边框.
                case HEADER:
                    if (this.lastRowType != RowType.HEADER) {
                        this.buildRowBorder(datas);
                    }
                    this.buildRowData(datas);
                    this.buildRowBorder(datas);
                    break;
                // 如果当前行是普通数据行，则需要考虑在最后一行处追加下边框.
                case DATA:
                    this.buildRowData(datas);
                    if (i == len - 1) {
                        this.buildRowBorder(datas);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 构建边框行的方法.
     *
     * @param datas 行
     */
    private void buildRowBorder(List<String> datas) {
        this.join.append("+");
        for (int i = 0, len = datas.size(); i < len; i++) {
            for (int j = 0; j < this.maxColMap.get(i) + 2; j++) {
                this.join.append("-");
            }
            this.join.append("+");
        }
        this.join.append("\n");
    }

    /**
     * 构建数据行的方法.
     *
     * @param datas 行数据
     */
    private void buildRowData(List<String> datas) {
        this.join.append("|");
        for (int i = 0, len = datas.size(); i < len; i++) {
            this.join.append(StrUtils.center(datas.get(i), this.maxColMap.get(i) + 2, ' '))
                    .append("|");
        }
        this.join.append("\n");
    }

    /**
     * 渲染出生成的结果.
     *
     * @return Table的ASCII字符串
     */
    public String render() {
        this.buildTable();
        return this.join.toString();
    }

    /**
     * 表格每行的类型和数据的实体类.
     */
    private static class Row {

        /** 行类型. */
        private RowType rowType;

        /** 行数据. */
        private List<String> datas;

        /**
         * 构造方法.
         *
         * @param rowType 行类型
         * @param datas 行数据集
         */
        Row(RowType rowType, List<String> datas) {
            this.rowType = rowType;
            this.datas = datas;
        }

    }

    /**
     * 区分是表头还是普通表格数据的枚举类.
     */
    private enum RowType {
        TITLE, HEADER, DATA
    }

    /**
     * 字符串工具类.
     */
    private static final class StrUtils {

        /**
         * 将某字符串置于给定大小的中间.
         *
         * @param str 字符串
         * @param size 总大小
         * @param padChar 填充字符
         * @return 字符串结果
         */
        private static String center(String str, int size, char padChar) {
            if (str != null && size > 0) {
                int strLen = str.length();
                int pads = size - strLen;
                if (pads > 0) {
                    str = leftPad(str, strLen + pads / 2, padChar);
                    str = rightPad(str, size, padChar);
                }
            }
            return str;
        }

        /**
         * 对给定的字符串和大小进行左填充.
         *
         * @param str 字符串
         * @param size 填充的总大小
         * @param padChar 填充字符
         * @return 填充结果
         */
        private static String leftPad(final String str, int size, char padChar) {
            int pads = size - str.length();
            return pads <= 0 ? str : repeat(padChar, pads).concat(str);
        }

        /**
         * 对给定的字符串和大小进行右填充.
         *
         * @param str 字符串
         * @param size 填充的总大小
         * @param padChar 填充字符
         * @return 填充结果
         */
        private static String rightPad(final String str, int size, char padChar) {
            int pads = size - str.length();
            return pads <= 0 ? str : str.concat(repeat(padChar, pads));
        }

        /**
         * 重复填充字符为字符串.
         *
         * @param ch 字符
         * @param repeat 重复次数
         * @return 字符串
         */
        private static String repeat(char ch, int repeat) {
            char[] buf = new char[repeat];
            for (int i = repeat - 1; i >= 0; i--) {
                buf[i] = ch;
            }
            return new String(buf);
        }

    }

}
