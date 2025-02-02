package com.project.webshopproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAsks is a Querydsl query type for Asks
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAsks extends EntityPathBase<Asks> {

    private static final long serialVersionUID = -893547300L;

    public static final QAsks asks = new QAsks("asks");

    public final StringPath adminResponse = createString("adminResponse");

    public final StringPath answer = createString("answer");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemId = createString("itemId");

    public final EnumPath<Asks.Status> status = createEnum("status", Asks.Status.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> userID = createNumber("userID", Long.class);

    public QAsks(String variable) {
        super(Asks.class, forVariable(variable));
    }

    public QAsks(Path<? extends Asks> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAsks(PathMetadata metadata) {
        super(Asks.class, metadata);
    }

}

