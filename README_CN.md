# mini-table

[![HitCount](http://hits.dwyl.io/blinkfox/mini-table.svg)](http://hits.dwyl.io/blinkfox/mini-table) [![GitHub license](https://img.shields.io/github/license/blinkfox/mini-table.svg)](https://github.com/blinkfox/mini-table/blob/master/LICENSE) ![Java Version](https://img.shields.io/badge/Java-%3E%3D%206-blue.svg)

[English Document](https://github.com/blinkfox/mini-table/blob/master/README.md)

> 一个轻量级、无任何依赖的 Java 纯文本表格(`ASCII TABLE`)生成库。

## 特性

- 轻量级、无依赖（jar包仅`13kb`）
- API简单易用
- 易于集成或定制修改，仅一个[Java](https://github.com/blinkfox/mini-table/blob/master/src/main/java/com/blinkfox/minitable/MiniTable.java)文件，且代码规范

## 集成使用

### Maven集成

```xml
<dependency>
    <groupId>com.blinkfox</groupId>
    <artifactId>mini-table</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

> **注**：目前版本还是`SNAPSHOT`，可直接`copy` Java 文件使用。

### API 使用

#### 示例1（无标题）

```java
String table = new MiniTable()
        .addHeaders("header1", "header2")
        .addDatas("col11", "col12")
        .addDatas("col21", "col22")
        .render();
System.out.println(table);
```

输出结果:

```bash
+---------+---------+
| header1 | header2 |
+---------+---------+
|  col11  |  col12  |
|  col21  |  col22  |
+---------+---------+
```

#### 示例2（有标题）

```java
String table = new MiniTable("The Title")
        .addHeaders("Name", "Sex", "Age", "Email", "Phone")
        .addDatas("LiLei", "male", 25, "lilei@gmail.com", "13809345219")
        .addDatas("hanMeiMei", "female", 23, "hmm@163.com", "13515343853")
        .addDatas("ZhangSan", "female", 32, "zhangsan@gmail.com", "13920199836")
        .render();
System.out.println(table);
```

输出结果:

```bash
+-------------------------------------------------------------+
|                          The Title                          |
+-----------+--------+-----+--------------------+-------------+
|   Name    |  Sex   | Age |       Email        |    Phone    |
+-----------+--------+-----+--------------------+-------------+
|   LiLei   |  male  | 25  |  lilei@gmail.com   | 13809345219 |
| hanMeiMei | female | 23  |    hmm@163.com     | 13515343853 |
| ZhangSan  | female | 32  | zhangsan@gmail.com | 13920199836 |
+-----------+--------+-----+--------------------+-------------+
```

## 许可证

本 [mini-table](https://github.com/blinkfox/mini-table) 类库遵守 [Apache License 2.0][http://www.apache.org/licenses/LICENSE-2.0] 许可证