//package cyr7.x86.pattern;
//
//import java.util.Optional;
//
//public interface Pattern {
//
//    boolean matches(Object... objs);
//
//    default boolean matchesOpts(Optional<?>... objs) {
//        Object[] unwrapped = new Object[objs.length];
//        for (int i = 0; i < objs.length; i++) {
//            Optional opt = objs[i];
//            if (opt.isEmpty()) {
//                return false;
//            }
//
//            unwrapped[i] = opt.get();
//        }
//
//        return matches(unwrapped);
//    }
//
//}
