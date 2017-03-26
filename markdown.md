一级标题
====================
二级标题
---------------------
**段落使用空行**

Now is the time for all good men to come to
the aid of their country. This is just a
regular paragraph.

# 锚点

The quick brown fox jumped over the lazy
dog's back.
**Markdown 支持两种标题的语法，Setext 和 atx 形式。
Setext 形式是用底线的形式，利用 = （最高阶标题）和 - （第二阶标题），Atx 形式在行首插入 1 到 6 个 # ，对应到标题 1 到 6 阶。**
### Header 3  三级标题

**区块引用则使用 email 形式的 '>' 角括号**
> This is a blockquote.
>
> This is the second paragraph in the blockquote.
>
> ## This is an H2 in a blockquote

__Markdown 使用星号和底线来标记需要强调的区段。__
Some of these words *are emphasized*.
Some of these words _are emphasized also_.
Use two asterisks for **strong emphasis**.
Or, if you prefer, __use two underscores instead__.


**无序列表使用星号、加号和减号来做为列表的项目标记，这些符号是都可以使用的，使用星号：**
* Candy.
* Gum.
* Booze.

+ Candy.
+ Gum.
+ Booze.

- Candy.
- Gum.
- Booze.

**有序的列表则是使用一般的数字接着一个英文句点作为项目标记：**
1. Red
2. Green
3. Blue

**如果你在项目之间插入空行，那项目的内容会用 <p> 包起来，你也可以在一个项目内放上多个段落，只要在它前面缩排 4 个空白或 1 个 tab 。**
* A list item.

    With multiple paragraphs.

* Another item in the list.

**Markdown 支援两种形式的链接语法： 行内 和 参考 两种形式，两种都是使用角括号来把文字转成连结。**
__行内形式是直接在后面用括号直接接上链接：__
This is an [example link](http://example.com/).

__你也可以选择性的加上 title 属性：__
This is an [example link](http://example.com/ "With a Title").

**参考形式的链接让你可以为链接定一个名称，之后你可以在文件的其他地方定义该链接的内容：**
I get 10 times more traffic from [Google][1] than from
[Yahoo][2] or [MSN][3].

[1]: http://google.com/ "Google"
[2]: http://search.yahoo.com/ "Yahoo Search"
[3]: http://search.msn.com/ "MSN Search"


title 属性是选择性的，链接名称可以用字母、数字和空格，但是不分大小写：
I start my morning with a cup of coffee and
[The New York Times][NY Times].

[ny times]: http://www.nytimes.com/

### 图片

**图片的语法和链接很像。**
行内形式（title 是选择性的）：
![alt text](/ui/skin/CASPIAN/images/helpDoc.png "Title")

参考形式
![alt text][id]

[id]: /path/to/img.jpg "Title"

代码

在一般的段落文字中，你可以使用反引号 ` 来标记代码区段，区段内的 &、< 和 > 都会被自动的转换成 HTML 实体，这项特性让你可以很容易的在代码区段内插入 HTML 码：

> __`<HTML>
    <HEAD></Head>
    <body>
    </body>
</HTML>`__

[问内链接](#user-content-一级标题);

表格

三个点转义

```

First Header | Second Header | Third Header
------------ | ------------- | ------------
Content Cell | Content Cell  | Content Cell
Content Cell | Content Cell  | Content Cell

```

First Header | Second Header | Third Header
------------ | ------------- | ------------
Content Cell | Content Cell  | Content Cell
Content Cell | Content Cell  | Content Cell

让表格两边内容对齐，中间内容居中，例如：

First Header | Second Header | Third Header
:----------- | :-----------: | -----------:
Left         | Center        | Right
Left         | Center        | Right

分割线

--------------

上下标
\^表示上标, _表示下标。如果上下标的内容多于一个字符，要用{}把这些内容括起来当成一个整体。上下标是可以嵌套的，也可以同时使用。 例如：

x^{y^z}=(1+{\rm e}^x)^{-2xy^w}

