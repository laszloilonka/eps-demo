package icell.hu.testdemo.helper;

import java.lang.reflect.Method;

/**
 * After the activity is destroyed, it stayed at heap.
 * <li>{@link android.view.inputmethod.InputMethodManager} still has references.</li>
 * This cases the memory leak.
 *
 * @see <a href="http://stackoverflow.com/questions/5038158/main-activity-is-not-garbage-collected-after-destruction-because-it-is-reference"
 *
 * Created by User on 2016. 10. 04..
 */
public class InputMethodReflector {
    public static final class TypedObject
    {
        private final Object object;
        private final Class type;

        public TypedObject(final Object object, final Class type)
        {
            this.object = object;
            this.type = type;
        }

        Object getObject()
        {
            return object;
        }

        Class getType()
        {
            return type;
        }
    }

    public static void invokeMethodExceptionSafe(final Object methodOwner, final String method, final TypedObject... arguments)
    {
        if (null == methodOwner)
        {
            return;
        }

        try
        {
            final Class<?>[] types = null == arguments ? new Class[0] : new Class[arguments.length];
            final Object[] objects = null == arguments ? new Object[0] : new Object[arguments.length];

            if (null != arguments)
            {
                for (int i = 0, limit = types.length; i < limit; i++)
                {
                    types[i] = arguments[i].getType();
                    objects[i] = arguments[i].getObject();
                }
            }

            final Method declaredMethod = methodOwner.getClass().getDeclaredMethod(method, types);

            declaredMethod.setAccessible(true);
            declaredMethod.invoke(methodOwner, objects);
        }
        catch (final Throwable ignored)
        {
        }
    }
}
