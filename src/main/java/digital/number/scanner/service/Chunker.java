package digital.number.scanner.service;

import java.util.Spliterator;
import java.util.function.Consumer;

public interface Chunker<T,R> {

  Consumer<Spliterator<R>> apply(Consumer<T> consumer);
}
