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
            return deCode(stream.toByteArray());
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private byte[] deCode(byte[] src){
        byte[] decode = null;
        decode = src;
        //do something niubility! 精密解码过程
        return decode;
    }


    //java 类热部署
    public static void main(String[] arg) {
        try {
            PathClassLoader pcl = new PathClassLoader("D:/");
            Class c1 = pcl.findClass("StdLib.BinaryDump");
            System.out.println("classpath =  D:/StdLib/BinaryDump.class");
            System.out.println(c1.newInstance());

            PathClassLoader pc2 = new PathClassLoader("D:/");
            Class c2 = pc2.findClass("StdLib.BinaryDump");
            System.out.println(c2.newInstance());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
