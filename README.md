# mini-table

[![HitCount](http://hits.dwyl.io/blinkfox/mini-table.svg)](http://hits.dwyl.io/blinkfox/mini-table) [![GitHub license](https://img.shields.io/github/license/blinkfox/mini-table.svg)](https://github.com/blinkfox/mini-table/blob/master/LICENSE) ![Java Version](https://img.shields.io/badge/Java-%3E%3D%206-blue.svg)

[中文文档](https://github.com/blinkfox/mini-table/blob/master/README_CN.md)

> A lightweight, no dependencies Java ASCII TABLE generation library.

## Features

- Lightweight, no dependencies (jar package only `13kb`)
- API is easy to use
- Easy to integrate or customize modifications, only one `Java` file, and code specification

## How to use

### Maven

```xml
<dependency>
    <groupId>com.blinkfox</groupId>
    <artifactId>mini-table</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

> **Note**: The current version is still `SNAPSHOT`, which can be used directly by the `copy` Java file.

### API

#### Demo1 (No Title)

```java
String table = new MiniTable()
        .addHeaders("header1", "header2")
        .addDatas("col11", "col12")
        .addDatas("col21", "col22")
        .render();
System.out.println(table);
```

Output Result:

```bash
+---------+---------+
| header1 | header2 |
+---------+---------+
|  col11  |  col12  |
|  col21  |  col22  |
+---------+---------+
```

#### Demo2（Titled）

```java
String table = new MiniTable("The Title")
        .addHeaders("Name", "Sex", "Age", "Email", "Phone")
        .addDatas("LiLei", "male", 25, "lilei@gmail.com", "13809345219")
        .addDatas("hanMeiMei", "female", 23, "hmm@163.com", "13515343853")
        .addDatas("ZhangSan", "female", 32, "zhangsan@gmail.com", "13920199836")
        .render();
System.out.println(table);
```

Output Result:

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

## License

本  类库遵守 [Apache License 2.0][http://www.apache.org/licenses/LICENSE-2.0] 许可证.
This [mini-table](https://github.com/blinkfox/mini-table) library is open sourced under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
