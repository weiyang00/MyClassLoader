import java.io.*;

/**
 * Created by weiyang on 2018/7/3.
 *
 * @Author: weiyang
 * @Package PACKAGE_NAME
 * @Project: ClassLoaderLearning
 * @Title:
 * @Description: 加载自定义路径下的class文件
 * @Date: 2018/7/3 19:15
 */
public class PathClassLoader extends ClassLoader {

    private String classpath;

    public PathClassLoader(String classpath){
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (classpath.startsWith(name)){
            byte[] classData = getData(name);
            if (classData == null ) {
                throw new ClassNotFoundException();
            } else  {
                return defineClass(name, classData, 0, classData.length);
            }
        } else {
            return super.loadClass(name);
        }
    }


    private byte[] getData(String className){
        String path = classpath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1){
                stream.write(buffer, 0 , num);
            }
            return stream.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] arg){
        Class cs = null;
        PathClassLoader pathClassLoader = new PathClassLoader("com.example");
        try {
            cs = pathClassLoader.findClass("message.class");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("this cs === " + cs);
    }

}
