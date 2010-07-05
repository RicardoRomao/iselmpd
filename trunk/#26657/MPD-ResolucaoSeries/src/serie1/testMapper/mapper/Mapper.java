package serie1.testMapper.mapper;

import exceptions.MapperException;
import serie1.extractor.IExtractor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

public final class Mapper<TSrc, TDest> implements IMapper<TSrc, TDest> {

    private final Class<?> _destClass;
    private final Class<?> _srcClass;
    private Map<String, IExtractor<TSrc>> _projections;

    public Mapper(Class<TSrc> srcClass, Class<TDest> destClass) {
        _destClass = destClass;
        _srcClass = srcClass;
    }

    public TDest map(TSrc srcObj) {
        TDest destObj;

        try {
            destObj = (TDest) _destClass.newInstance();

        } catch (InstantiationException ex) {
            throw new MapperException();
        } catch (IllegalAccessException ex) {
            throw new MapperException();
        }

        String destMethodName = null;
        String fieldName = null;

        Method srcGetter;
        Method[] methods = _destClass.getMethods();

        for (Method m : methods) {

            destMethodName = m.getName();
            // Tests if method on destObj is a setter
            if (destMethodName.startsWith("set") && destMethodName.charAt(3) >= "A".charAt(0)) {

                //If it is saves the field name
                fieldName = destMethodName.substring(3, destMethodName.length());
                try {
                    srcGetter = _srcClass.getMethod(Utils.getGetterName(fieldName));

                    //Invoke the setter on destObj with the return of getter on srcObj
                    m.invoke(destObj, srcGetter.invoke(srcObj));
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return destObj;
    }

    public void addPropertyProjection(String destPropName, IExtractor<TSrc> srcPropExtractor) {
        if (_projections == null) {
            _projections = new Hashtable<String, IExtractor<TSrc>>();
        }
        _projections.put(destPropName, srcPropExtractor);
    }

    private void doProjections(TDest destObj) {
        if (_projections != null) {
            Method destSetter;
            for (Map.Entry<String, IExtractor<TSrc>> _entry : _projections.entrySet()) {
            }


            //vai invocar o getter em destobj passando-lhe o retorn de getValue de IExtractor
        }
    }
}
