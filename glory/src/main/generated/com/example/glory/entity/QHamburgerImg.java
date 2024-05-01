package com.example.glory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHamburgerImg is a Querydsl query type for HamburgerImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHamburgerImg extends EntityPathBase<HamburgerImg> {

    private static final long serialVersionUID = 1459767575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHamburgerImg hamburgerImg = new QHamburgerImg("hamburgerImg");

    public final QHamburger hamburger;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath oriImgName = createString("oriImgName");

    public final StringPath repImgYn = createString("repImgYn");

    public QHamburgerImg(String variable) {
        this(HamburgerImg.class, forVariable(variable), INITS);
    }

    public QHamburgerImg(Path<? extends HamburgerImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHamburgerImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHamburgerImg(PathMetadata metadata, PathInits inits) {
        this(HamburgerImg.class, metadata, inits);
    }

    public QHamburgerImg(Class<? extends HamburgerImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hamburger = inits.isInitialized("hamburger") ? new QHamburger(forProperty("hamburger")) : null;
    }

}

