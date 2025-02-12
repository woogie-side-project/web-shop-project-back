package com.project.webshopproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAsk is a Querydsl query type for Ask
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAsk extends EntityPathBase<Ask> {

    private static final long serialVersionUID = 1218101879L;

    public static final QAsk ask = new QAsk("ask");

    public final StringPath adminResponse = createString("adminResponse");

    public final StringPath answer = createString("answer");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAsk(String variable) {
        super(Ask.class, forVariable(variable));
    }

    public QAsk(Path<? extends Ask> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAsk(PathMetadata metadata) {
        super(Ask.class, metadata);
    }

}

