package click.benshop.bebenshop.bases;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Getter
public final class BaseListProduceDto<T> {
    private final List<T> content;
    private final Integer page;
    private final Integer size;
    private final Integer totalPages;
    private final Long totalElements;

    public static <T> BaseListProduceDto<T> buildEmpty(Pageable pageable) {
        return BaseListProduceDto
                .<T>builder()
                .content(Collections.emptyList())
                .totalElements(0L)
                .totalPages(0)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    public static <T> BaseListProduceDto<T> build(Page<T> page) {
        return BaseListProduceDto
                .<T>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .build();
    }

    public static <T> BaseListProduceDto<T> build(Page<T> page, List<T> content) {
        return BaseListProduceDto
                .<T>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .content(content)
                .build();
    }

    public Integer getTotalPages() {
        if (Objects.nonNull(size) && Objects.nonNull(totalElements)) {
            return size == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) size);
        }
        return totalPages;
    }

    public <R> BaseListProduceDto<R> map(Function<T, R> mapFunc) {
        List<R> newContent = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(content)) {
            try {
                newContent.addAll(content.stream().map(mapFunc).collect(Collectors.toList()));
            } catch (Exception ignored) {

            }
        }

        return BaseListProduceDto
                .<R>builder()
                .content(newContent)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(getTotalPages())
                .build();
    }
}