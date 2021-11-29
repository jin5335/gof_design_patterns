import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BasicObservableSet<E> extends ForwardingSet<E> implements ObservableSet<E> {
    public BasicObservableSet(Set<E> set) { super(set); }
    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        observers.add(observer);
    }

    public boolean removeObserver(SetObserver<E> observer) {
        return observers.remove(observer);
    }

    public void notifyElementAdded(E element) {
        for (SetObserver<E> observer : observers)
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
