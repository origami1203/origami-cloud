package org.origami.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用树形工具类
 *
 * @author origami
 * @date 2023/5/6 17:38
 */
public abstract class TreeUtil {


    private TreeUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }

    /**
     * 列表转树
     *
     * @param <T>         实体类泛型
     * @param <ID>>       实体类的id的类型
     * @param sourceList  源列表
     * @param rootFunc    判断是否是根节点的函数
     * @param mappingFunc 实体类T转{@link Node}的映射函数
     * @return {@code List<Node<ID>>}
     * @see TreeUtil#listToTree(List, Predicate, Function, boolean)
     */
    public static <T, ID> List<Node<ID>> listToTree(List<T> sourceList, Predicate<T> rootFunc,
            Function<T, Node<ID>> mappingFunc) {
        return listToTree(sourceList, rootFunc, mappingFunc, false);
    }


    /**
     * 列表转树
     *
     * @param <T>         实体类泛型
     * @param <ID>>       实体类的id的类型
     * @param sourceList  源列表
     * @param rootFunc    判断是否是根节点的函数
     * @param mappingFunc 实体类T转{@link Node}的映射函数
     * @param strictMode  是否使用严格模式，若为true，当存在游离的、不属于tree的node时，会抛出异常
     * @return {@code List<Node<ID>>}，存在游离node时会抛出异常
     */
    public static <T, ID> List<Node<ID>> listToTree(List<T> sourceList, Predicate<T> rootFunc,
            Function<T, Node<ID>> mappingFunc, boolean strictMode) {

        if (CollUtil.isEmpty(sourceList)) {
            return Collections.emptyList();
        }

        Assert.nonNull(rootFunc, "root function cannot be null");
        Assert.nonNull(mappingFunc, "mapping function cannot be null");

        List<Node<ID>> rootList = new ArrayList<>();
        Map<ID, Node<ID>> idNodeMap = new HashMap<>(sourceList.size());

        sourceList.forEach(it -> {
            Node<ID> node = mappingFunc.apply(it);

            if (rootFunc.test(it)) {
                rootList.add(node);
            }
            idNodeMap.put(node.getId(), node);
        });

        Assert.notEmpty(rootList, "there is no root node exists");

        AtomicInteger count = new AtomicInteger();

        idNodeMap.forEach((id, node) -> {

            Node<ID> parentNode = idNodeMap.get(node.getParentId());

            if (Objects.nonNull(parentNode)) {
                parentNode.getChildren().add(node);
                count.getAndIncrement();
            }
        });

        // 当存在游离的不属于整个树的节点时抛出异常
        if (strictMode) {
            Assert.isTrue(idNodeMap.size() - rootList.size() == count.get(),
                    "there are nodes that are not connected to the tree and have no parent node.");
        }

        return rootList;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class Node<T> {

        private T id;
        private T parentId;
        private String name;
        private Map<String, Object> extra;
        private List<Node<T>> children;

        public Node(T id, T parentId, String name) {
            this(id, parentId, name, null, null);
        }

        public Node(T id, T parentId, String name, Map<String, Object> extra,
                List<Node<T>> children) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.extra = extra;
            this.children = Optional.ofNullable(children).orElseGet(ArrayList::new);
        }
    }

}
