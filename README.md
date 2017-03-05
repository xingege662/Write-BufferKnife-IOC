# Write-BufferKnife-IOC
# 手写butterKnife框架中的bindView<br>
之前写了Xutils框架的注解，通过反射和注解的方式来将findViewById交给第三方来接手，但是反射多了会影响一些性能，随后有了ButterKnife和Dogger这些牛逼的注解框架，其过程是在编译时期通过拼接的方式生成代码。记录一次。<br>
核心原理
-------
  APT(Annotion ProcessingTools)实现，编译时，Annotation解析的基本原理是，在某些代码上(如类型，函数，字段等)添加注释，在编译时编译器会检查AbstractProcessor的子类，并且调用该类行的Process函数，然后将添加了注解的所有元素传递到process函数中，使得开发人员可以在编译器进行相应的处理，例如，根据注解生成新的java类，这也是ButterKnife的基本原理<br>

APT工具
-------
  APT是一种处理注解的工具，它对源代码进行检测找出其中的Annotation，使用Annotation进行额外的处理。Annotation处理器在处理Annotaion时可以根据源文件中的Annotaion生成额外的源文件和其他的文件，具体内容由Annotation处理器的编写决定。APT还会编译生成的源文件和原来的源文件，将他们生成class文件。<br>
Java源文件编译成Class文件
----------------------
工具是javac工具<br>
注解处理器是一个在javac中的，用来编译时扫描和处理的注解的工具，可以为特定的注解，注册你自己的注解处理器<br>
在inject-compile中是核心代码，在编译时会进行生成代码<br>
一直有一个理念，给了代码，只要不是太烂，是最好的提高方式！<br>
Reading the fuck source code!!!
-------------------------------
