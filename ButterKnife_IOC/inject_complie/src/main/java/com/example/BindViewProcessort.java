package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by xinchang on 2017/3/5.
 */
@AutoService(Processor.class)

public class BindViewProcessort extends AbstractProcessor {
    //处理节点的工具类
    private Elements elementsUtils;
    private Types types;
    //自动生成java文件的辅助类
    private Filer filter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
        filter = processingEnvironment.getFiler();
    }

    /**
     * 注解处理器处理哪些注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //存放着app所有的注解类型的成员变量
        Map<TypeElement, List<FiledViewBinding>> target = new HashMap<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {

            FileUtils.print(element.getSimpleName().toString());
            //拿到element所存在的类
            TypeElement enClosingElement = (TypeElement) element.getEnclosingElement();
            //然后拿注解
            List<FiledViewBinding> list = target.get(enClosingElement);
            if (list == null) {
                list = new ArrayList<>();
                target.put(enClosingElement, list);
            }
            String packgeName = getPacageName(enClosingElement);
            int id = element.getAnnotation(BindView.class).value();
            String fieldName = element.getSimpleName().toString();
            TypeMirror typeMirror = element.asType();
            FiledViewBinding filedViewBinding = new FiledViewBinding(fieldName, typeMirror, id);
            list.add(filedViewBinding);
            FileUtils.print(typeMirror.getKind().toString());

        }

        for (Map.Entry<TypeElement, List<FiledViewBinding>> item : target.entrySet()) {
            List<FiledViewBinding> list = item.getValue();
            if (list == null || list.size() == 0) {
                continue;
            }
            //activity
            TypeElement enClosingElement = item.getKey();
            String packageName = getPacageName(enClosingElement);
            String completeClassName = getClassName(enClosingElement, packageName);

            ClassName className = ClassName.bestGuess(completeClassName);

            ClassName viewBinder = ClassName.get("com.ioc.xinchang.inject", "ViewBinder");
            //开始构建java文件
            //从外层包名  类名开始构建
            TypeSpec.Builder result = TypeSpec.classBuilder(completeClassName + "$$ViewBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addTypeVariable(TypeVariableName.get("T", className))
                    .addSuperinterface(ParameterizedTypeName.get(viewBinder, className));
            //构建方法
            MethodSpec.Builder methodBuildter = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addAnnotation(Override.class)
                    .addParameter(className, "target", Modifier.FINAL);
            //构建方法中的代码
            for (int i = 0; i < list.size(); i++) {
                FiledViewBinding filedViewBinding = list.get(i);
                //android.text.TextView
                String packageNameString = filedViewBinding.getTypeMirror().toString();

                ClassName viewClass = ClassName.bestGuess(packageNameString);
                methodBuildter.addStatement("target.$L=($T)target.findViewById($L)",
                        filedViewBinding.getName(), viewClass, filedViewBinding.getResID());
            }
            result.addMethod(methodBuildter.build());
            try {
                JavaFile.builder(packageName, result.build())
                        .addFileComment("auto create")
                        .build().writeTo(filter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * MainActivity$$ViewBinder
     *
     * @param enClosingElement
     * @param packageName
     * @return
     */
    private String getClassName(TypeElement enClosingElement, String packageName) {
        int packageLenth = packageName.length() + 1;

        return enClosingElement.getQualifiedName().toString().substring(packageLenth).replace(".", "$$");
    }

    private String getPacageName(TypeElement enClosingElement) {
        return elementsUtils.getPackageOf(enClosingElement).getQualifiedName().toString();
    }
}
