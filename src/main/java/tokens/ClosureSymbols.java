package tokens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClosureSymbols {
    public static final Map<String, List<String>> clojureSymbols = new HashMap<>();

    static {
        clojureSymbols.put("operator",
                Arrays.asList("+", "-", "*", "/", "quot", "rem", "mod", "inc", "dec", "max", "min", "=", "==", "not=", "<", ">", "<=", ">=", "compare",
                        "and", "or", "not", "->", "->>", "as->", "cond->", "cond->>", "some->", "some->>", "bit-and", "bit-or", "bit-xor", "bit-not",
                        "bit-flip", "bit-set", "bit-shift-right", "bit-shift-left", "bit-clear", "bit-test", "bit-and-not", "unsigned-bit-shift-right" ));
        clojureSymbols.put("keywords", Arrays.asList("def", "defn", "defn-", "let", "letfn", "declare", "ns", "if", "if-not", "when", "when-not", "when-let", "when-first", "if-let", "cond", "condp", "case", "when-some", "if-some", "map", "map-indexed", "reduce", "for", "doseq", "dotimes", "while", "true?", "false?", "instance?", "nil?", "some?", "int", "zero?", "pos?", "neg?", "even?", "odd?", "number?", "integer?", "rand", "rand-int", "atom", "swap!", "reset!", "compare-and-set!",
                "add-watch", "remove-watch", "set-validator!", "get-validator", "fn", "defn", "defn-", "identity", "constantly", "comp", "complement", "partial", "juxt", "memoize", "fnil", "every-pred", "some-fn", "apply", "fn?", "ifn?", "str", "name", "count", "get", "subs", "join", "escape", "split", "split-lines", "replace", "replace-first", "reverse", "re-find", "re-seq", "re-matches", "re-pattern", "replace", "replace-first", "capitalize", "lower-case", "upper-case",
                "trim", "trim-newline", "triml", "trimr", "string?", "blank?", "starts-with?", "ends-with?", "includes?", "try", "catch", "finally", "throw", "count", "empty", "not-empty", "into", "conj", "distinct?", "empty?", "every?", "not-every?", "somenot-any?", "sequential?", "associative?", "sorted?", "counted?", "reversible?", "coll?", "list?", "vector?", "set?", "map?", "seq?", "vector", "vec", "get", "peek", "assoc", "pop", "subvec", "replace", "conj", "rseq", "mapv", "filterv", "reduce-kv", "list", "list*", "first", "nth", "peek", "cons", "conj", "rest", "pop", "set", "hash-set", "sorted-set", "sorted-set-by", "contains?", "disj", "union", "difference", "intersection", "select", "subset?", "superset?", "hash-map", "array-map", "zipmap", "sorted-map", "sorted-map-by", "frequencies", "group-by", "get-in", "contains?", "find", "keys", "vals", "assoc", "assoc-in", "dissoc", "merge",
                "merge-with", "select-keys", "update-in", "rseq", "subseq", "rsubseq","distinct", "filter", "remove", "take-nth", "for", "const", "conj", "concat", "lazy-cat", "mapcat", "cycle", "interleave", "interpose",
                "rest", "nthrest", "next", "fnext", "nnext", "drop", "drop-while", "take-last", "for",
                "take", "take-while", "butlast", "drop-last", "for",
                "conj", "concat", "distinct", "flatten", "group-by", "partition", "partition-all", "partition-by", "split-at", "split-with", "filter",
                "remove", "replace", "shuffle", "reverse", "sort", "sort-by", "compare", "map", "map-indexed", "mapcat", "for", "replace", "first",
                "second", "last", "rest", "next", "ffirst", "nfirst", "fnext", "nnext", "nth", "nthnext", "rand-nth", "when-first", "max-key", "min-key",
                "zipmap", "into", "reduce", "reductions", "set", "vec", "into-array", "to-array-2d", "apply", "some", "filter", "doseq", "dorun", "doall",
                "realized?", "seq", "vals", "keys", "rseq", "subseq", "rsubseq", "array-seq", "prim-seq", "lazy-seq", "repeatedly", "iterate", "repeat",
                "range", "re-seq", "tree-seq", "keep", "keep-indexed"));
        clojureSymbols.put("collections", Arrays.asList("(", ")", "[", "]", "{", "}"));
    }
}
