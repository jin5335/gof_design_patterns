import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MinSyncObservableSet<E> extends ForwardingSet<E> implements ObservableSet<E> {
    public MinSyncObservableSet(Set<E> set) { super(set); }
    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    public void notifyElementAdded(E element) {
        List<SetObserver<E>> snapshot = null;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        for (SetObserver<E> observer : snapshot)
            observer.added(this, element);
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