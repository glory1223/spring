package com.example.glory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserveHamburger is a Querydsl query type for ReserveHamburger
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserveHamburger extends EntityPathBase<ReserveHamburger> {

    private static final long serialVersionUID = 1520575050L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserveHamburger reserveHamburger = new QReserveHamburger("reserveHamburger");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final QReserve reserve;

    public final NumberPath<Integer> reservePrice = createNumber("reservePrice", Integer.class);

    public QReserveHamburger(String variable) {
        this(ReserveHamburger.class, forVariable(variable), INITS);
    }

    public QReserveHamburger(Path<? extends ReserveHamburger> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserveHamburger(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserveHamburger(PathMetadata metadata, PathInits inits) {
        this(ReserveHamburger.class, metadata, inits);
    }

    public QReserveHamburger(Class<? extends ReserveHamburger> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reserve = inits.isInitialized("reserve") ? new QReserve(forProperty("reserve"), inits.get("reserve")) : null;
    }

}

