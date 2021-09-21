package digital.number.scanner.service;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Chunker<T, A, B> extends Function<Consumer<T>, BiConsumer<A, B>> {

  BiConsumer<A, B> apply(Consumer<T> consumer);
}
