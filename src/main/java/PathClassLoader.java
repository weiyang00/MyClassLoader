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

    public PathClassLoader(){
        this.classpath = "";
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classData = getData(name);
            if (classData == null ) {
                throw new ClassNotFoundException();
            } else  {
                return defineClass(name, classData, 0, classData.length);
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

    public static void main(String[] arg) {
        try {
            ClassLoader pcl = new PathClassLoader("D:/");
            Class c = pcl.loadClass("PathClassLoader");
            System.out.println(c.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
