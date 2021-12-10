import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]) throws Exception {
        //BasicObservableSet<Integer> set = new BasicObservableSet<>(new HashSet<>());
        //SynchronizedObservableSet<Integer> set = new SynchronizedObservableSet<>(new HashSet<>());
        MinSyncObservableSet<Integer> set = new MinSyncObservableSet<>(new HashSet<>());

        // Example 1 - 1
//        ExecutorService exec = Executors.newFixedThreadPool(4);
//        exec.submit(() -> {
//            System.out.println(Thread.currentThread().getName());
//            for (int i = 0; i < 100; ++i) {
//                String name="observer" + i;
//                set.addObserver((s, e) -> System.out.println(name + ":" + e));
//            }
//        });
//
//        exec.submit(() -> {
//            System.out.println(Thread.currentThread().getName());
//            for (int i = 0; i < 100; ++i) {
//                String name="*observer" + i;
//                set.addObserver((s, e) -> System.out.println(name + ":" + e));
//            }
//        });
//
//        try {
//            if(exec.awaitTermination(1, TimeUnit.SECONDS)) {
//                System.out.println("Running....");
//            }else{
//                System.out.println("Done!");
//                exec.shutdownNow();
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("num_observer: " + set.getNumObservers());

//        // Example 1-2
//        for (int i = 0; i < 100; i++){
//            String name="observer" + i;
//            set.addObserver((s, e) -> System.out.println(name + ":" + e));
//        }
//        System.out.println("main:" + Thread.currentThread().getName());
//        set.add(0);
//
//        ExecutorService exec = Executors.newFixedThreadPool(4);
//        exec.submit(() -> {
//            System.out.println(Thread.currentThread().getName());
//            for (int i = 0; i < 10; ++i) {
//                set.add(i);
//            }
//        });
//
//        exec.submit(() -> {
//            System.out.println(Thread.currentThread().getName());
//            String name="*_observer";
//            set.addObserver((s, e) -> System.out.println(name + ":" + e));
//        });
//
//        try {
//            if(exec.awaitTermination(5, TimeUnit.SECONDS)) {
//                System.out.println("Running....");
//            }else{
//                System.out.println("Done!");
//                exec.shutdownNow();
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        // 책 예제 1 - 동기화 블록은 동시 수정은 막아 주지만 자신의 콜백을 통해 수정 하는 것은 막지 못한다.
        set.addObserver((s, e) -> System.out.println("observer1:" + e));

        // item = 3이 추가 되면 set(=s)을 구독 해지 하는 observer
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> s, Integer e) {
               System.out.println("*observer1:" + e);
               if(e == 3)
                   s.removeObserver(this);
            }
        });

        for(int i = 0; i < 5; ++i) {
            set.add(i);
        }

       // 책 예제 2 - 다른 thread를 사용 (deadlock)
//       set.addObserver((s, e) -> System.out.println("observer2:" + e));
//       set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet<Integer> s, Integer e) {
//                System.out.println("*observer2:" + e);
//                if (e == 3) {
//                    ExecutorService exec =
//                            Executors.newSingleThreadExecutor();
//                    try {
//                        exec.submit(() -> s.removeObserver(this)).get();
//                    } catch (ExecutionException | InterruptedException ex) {
//                        throw new AssertionError(ex);
//                    } finally {
//                        exec.shutdown();
//                    }
//                }
//            }
//        });
//
//        for(int i = 0; i < 5; ++i) {
//            set.add(i);
//        }
    }
}

//class IncrementInt {
//    public int val;
//
//    public IncrementInt() {
//        this.val = 0;
//    }
//
//    public void plusOne() {
//        this.val += 1;
//
//    }
//}
//
//public class Main {
//    public static void main(String args[]) throws Exception {
//        IncrementInt incrementInt = new IncrementInt();
//
//        ExecutorService exec = Executors.newFixedThreadPool(4);
//        exec.submit(() -> {
//            for (int i = 0; i < 10000; ++i) {
//                incrementInt.plusOne();
//            }
//        });
//
//        exec.submit(() -> {
//            for (int i = 0; i < 10000; ++i) {
//                incrementInt.plusOne();
//            }
//        });
//
//        try {
//            if(exec.awaitTermination(1, TimeUnit.SECONDS)) {
//                System.out.println("incrementInt.val: " + incrementInt.val);
//                System.out.println("Running....");
//            }else{
//                System.out.println("incrementInt.val: " + incrementInt.val);
//                System.out.println("Done");
//                exec.shutdownNow();
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//}