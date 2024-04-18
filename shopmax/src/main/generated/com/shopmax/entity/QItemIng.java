package com.shopmax.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemIng is a Querydsl query type for ItemIng
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemIng extends EntityPathBase<ItemIng> {

    private static final long serialVersionUID = 1011814129L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemIng itemIng = new QItemIng("itemIng");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final QItem item;

    public final StringPath oriImgName = createString("oriImgName");

    public final StringPath repImgYn = createString("repImgYn");

    public QItemIng(String variable) {
        this(ItemIng.class, forVariable(variable), INITS);
    }

    public QItemIng(Path<? extends ItemIng> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemIng(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemIng(PathMetadata metadata, PathInits inits) {
        this(ItemIng.class, metadata, inits);
    }

    public QItemIng(Class<? extends ItemIng> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
    }

}

