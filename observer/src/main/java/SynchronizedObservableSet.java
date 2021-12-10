import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SynchronizedObservableSet<E> extends ForwardingSet<E> implements ObservableSet<E>{
    public SynchronizedObservableSet(Set<E> set) { super(set); }
    private final List<SetObserver<E>> observers = new ArrayList<>();

    public int getNumObservers(){
        return observers.size();
    }

    @Override
    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public boolean removeObserver(SetObserver<E> observer) {
        //System.out.println("r:" + Thread.currentThread().getName());
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    @Override
    // main -> add(3) -> notify ->  ob2.added -> removeObser(ob2)
    // StringBuffer(동기화o) -> StringBuilder(동기화x)
    public void notifyElementAdded(E element) {
        //System.out.println(Thread.currentThread().getName());
        synchronized (observers) {
            for (SetObserver<E> observer : observers)
                observer.added(this, element);
        }
    }

    @Override public boolean add(E element) {
        boolean added = super.add(element);
        if (added) {
            notifyElementAdded(element);
        }
        return added;
    }

    @Override public boolean addAll(Collection<? extends E> c){
        boolean result = false;
        for (E element : c){
            result |= add(element);
        }
        return result;
    }
}
