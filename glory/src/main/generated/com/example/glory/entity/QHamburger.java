package com.example.glory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHamburger is a Querydsl query type for Hamburger
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHamburger extends EntityPathBase<Hamburger> {

    private static final long serialVersionUID = 950561548L;

    public static final QHamburger hamburger = new QHamburger("hamburger");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final EnumPath<com.example.glory.constant.HamburgerCategory> hamburgerCategory = createEnum("hamburgerCategory", com.example.glory.constant.HamburgerCategory.class);

    public final StringPath hamburgerDetail = createString("hamburgerDetail");

    public final StringPath hamburgerNm = createString("hamburgerNm");

    public final EnumPath<com.example.glory.constant.HamburgerStatus> hamburgerStatus = createEnum("hamburgerStatus", com.example.glory.constant.HamburgerStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QHamburger(String variable) {
        super(Hamburger.class, forVariable(variable));
    }

    public QHamburger(Path<? extends Hamburger> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHamburger(PathMetadata metadata) {
        super(Hamburger.class, metadata);
    }

}

