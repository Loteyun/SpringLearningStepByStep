package com.yun.springframework.context;

import com.yun.springframework.stereotype.MyApplicationContext;
import com.yun.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-05 14:52
 */
public class MyAnnotationConfigApplicationContext implements MyApplicationContext {

    private Map<String,Object> beanMap=new HashMap<String,Object>();

    public MyAnnotationConfigApplicationContext(Class<?>... componentClasses){
        try {
            register(componentClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register(Class<?>[] componentClasses) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ClassNotFoundException {
        //实现代码
        //版本1：只实现IOC，MyPostConstruct MyPreDestory
        if(componentClasses==null ||componentClasses.length<=0){
            throw new RuntimeException("没有指定配置类");
        }
        for(Class cl:componentClasses){
            if(!cl.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }
            String[] basePackages=getAoppConfigBasePackages(cl);
            if(cl.isAnnotationPresent(MyComponentScan.class)){
                MyComponentScan mcs=(MyComponentScan)cl.getAnnotation(MyComponentScan.class);
                if(mcs.basePackages()!=null&& mcs.basePackages().length>0){
                    basePackages=mcs.basePackages();
                }
            }
            //处理MyBean的情况
            Object obj=cl.newInstance();
            handleAtMyBean(cl,obj);
            for(String basePackage :basePackages){
                scanPackageAndSubPackageClasses(basePackage);
            }
            handleManageBean();
            //版本2:实现di  循环beanMap中每个bean ,找到他们每个勒种的每个由@Autowired @Rsource注解的方法返回以实现di
            handleDi(beanMap);
        }




    }

    private void handleDi(Map<String, Object> beanMap) throws InvocationTargetException, IllegalAccessException {
        Collection<Object> objectCollection=beanMap.values();
        for(Object obj:objectCollection){
            Class cls=obj.getClass();
            Method[] ms=cls.getDeclaredMethods();
            for(Method m:ms){
                if(m.isAnnotationPresent(MyAutowired.class) && m.getName().startsWith("set")){
                    invokeAutowiredMethod(m,obj);
                }else if(m.isAnnotationPresent(MyResource.class) && m.getName().startsWith("set")){
                    invokeResourceMethod(m,obj);
                }
            }
            Field[] f=cls.getDeclaredFields();
            for(Field field:f){
                if(field.isAnnotationPresent(MyAutowired.class)){

                }else if(field.isAnnotationPresent(MyResource.class)){

                }
            }
        }
    }

    private void invokeResourceMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //取出 MyResource 中的name属性值，当成 beanID
        MyResource mr=m.getAnnotation(MyResource.class);
        String beanId=mr.name();
        //如果没有，则取出 m方法中那参数的雷兴明，改成首字小写 当成beanId
        if(beanId==null || beanId.equalsIgnoreCase("")){
            String pname=m.getParameterTypes()[0].getSimpleName();
            beanId =pname.substring(0,1).toLowerCase()+pname.substring(1);
        }
        // 从beanMap中取出
        Object o=beanMap.get(beanId);
        m.invoke(obj,o);
    }

    private void invokeAutowiredMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.取出 m的参数类型
        Class typeClass=m.getParameterTypes()[0];
        //2.从beanMap中循环所有的Object，
        Set<String> keys =beanMap.keySet();

        for(String key:keys){
            //4.如果是，则从beanMap取出
            Object o=beanMap.get(key);
            //3.判断这些object是否为参数类型的实s例 instanceof
            if(o.getClass().getName().equalsIgnoreCase(typeClass.getName())){
                m.invoke(obj,o);
            }
        }
    }

    private Set<Class> managedBeanClasses=new HashSet<Class>();
    private void handleManageBean() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Class c:managedBeanClasses){
            if(c.isAnnotationPresent(MyComponent.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(MyService.class)){
                saveManagedBean(c);
            }
            else if(c.isAnnotationPresent(MyRespository.class)){
                saveManagedBean(c);
            }
            else if(c.isAnnotationPresent(MyController.class)){
                saveManagedBean(c);
            }

        }
    }

    private void saveManagedBean(Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o=c.newInstance();
        handlePostConstruct(o,c);
        String beanId =c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        beanMap.put(beanId,o);
    }

    private void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath =basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径"+basePackage+"\n替换后:"+packagePath);
        Enumeration<URL> files=Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while(files.hasMoreElements()){
            URL url=files.nextElement();
            System.out.println("配置的扫描路径为："+url.getFile());
            //TODO: 递归这些目录
            findClassesInPackages(url.getFile(),basePackage);
        }
        
    }

    private void findClassesInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f=new File(file);
        //File[] fs=f.listFiles();
        File[] classFiles=f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class") || file.isDirectory();         }
        });
        System.out.println(classFiles);
        for(File cf:classFiles){
            if (cf.isDirectory()){
                //如果目录则 递归
                // 拼读子目录
                basePackage+="."+cf.getName().substring(cf.getName().lastIndexOf("/"),1);
                findClassesInPackages(cf.getAbsolutePath(),basePackage);
            }else{
                //加载 cf 作为class文件
                URL[] urls=new URL[]{};
                URLClassLoader url=new URLClassLoader(urls);
                //com.yc.bean.hello.class -> com.yc.bean.hello
                Class c=url.loadClass(basePackage+"."+cf.getName().replace(".class",""));
                managedBeanClasses.add(c);
            }
        }

    }


    private void handleAtMyBean(Class cls, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.获取cls中所有的Method
        Method[] ms=cls.getDeclaredMethods();
        //2.循环、判断 每个method上是否有@MyBean注解
        for(Method m:ms){
            if(m.isAnnotationPresent(MyBean.class)){
                //3.有  则invoke它，它有返回值 将返回值存到 beanMap中，键是方法名 ，值是返回值 对象
                Object o=m.invoke(obj);
                //TODO: 加入处理 @MyBean注解对应的方法所实例化的类中的@MyPostConstruct 对应的方法
                handlePostConstruct(o,o.getClass());
                beanMap.put(m.getName(),o);
            }
        }
    }

    private void handlePostConstruct(Object o, Class<?> Cls) throws InvocationTargetException, IllegalAccessException {
        Method[] ms= Cls.getDeclaredMethods();
        for(Method m:ms){
            if(m.isAnnotationPresent(MyPostConStruct.class)){
                m.invoke(o);
            }
        }

    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
    private String[] getAoppConfigBasePackages(Class cl){
        String[] paths=new String[1];
        paths[0]=cl.getPackage().getName();
        return paths;
    }
}
