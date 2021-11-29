import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String args[]) throws Exception {
        //BasicObservableSet<Integer> set = new BasicObservableSet<>(new HashSet<>());
        SynchronizedObservableSet<Integer> set = new SynchronizedObservableSet<>(new HashSet<>());
        //MinSyncObservableSet<Integer> set = new MinSyncObservableSet<>(new HashSet<>());


        set.addObserver((s, e) -> System.out.println("observer1:" + e));
        set.addObserver((s, e) -> System.out.println("observer2:" + e));

        for (int i=1; i<20; i++){
            String name="observer"+i;
            set.addObserver((s, e) -> System.out.println(name + ":" + e));
        }

        ExecutorService exec = Executors.newSingleThreadExecutor();
        try {
            exec.submit(() -> {
                for (int i = 1; i < 5; i++) {
                    set.add(i);
//                    String observerName = "observer" + i + ":";
//                    set.addObserver((s, e) -> System.out.println(observerName + e));
                }
            });
        } catch (Exception e) {
          System.out.print(e);
        } finally {
            exec.shutdown();
        }

        set.addObserver((s, e) -> System.out.println("observer_new:" + e));

        set.add(5);

        Thread.sleep(5000);
//        set.addObserver((s, e) -> System.out.println("observer2:" + e));

//        set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet<Integer> s, Integer e) {
//               System.out.println(e);
//               if(e == 3)
//                   s.removeObserver(this);
//            }
//        });

//        set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet<Integer> s, Integer e) {
//                System.out.println("observer4: " + e);
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
    }
}
