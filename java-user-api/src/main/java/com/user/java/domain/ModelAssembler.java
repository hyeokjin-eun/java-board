package com.user.java.domain;

/*
@Component
public class ModelAssembler<Res, Entity, Controller> implements RepresentationModelAssembler<Entity, EntityModel<Res>> {

    public ModelAssembler(Class<Controller> controller) {
        super(controller, Class<Res>);
    }

    @Override
    public EntityModel<Res> toModel(Entity entity) {
        return null;
    }

    public CollectionModel<EntityModel<Res>> toCollectionModel(List<EntityModel<Res>> res, String path) {
        return new CollectionModel<>(res,
                linkTo(CrudController.class).slash(path).withSelfRel());
    }

    public EntityModel<Res> toModel(Res res, Links links) {
        return new EntityModel<>(res, links);
    }
}*/
