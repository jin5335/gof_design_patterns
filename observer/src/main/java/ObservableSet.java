public interface ObservableSet<E> {
    abstract public void addObserver(SetObserver<E> observer);
    abstract public boolean removeObserver(SetObserver<E> observer);
    abstract public void notifyElementAdded(E element);
}
