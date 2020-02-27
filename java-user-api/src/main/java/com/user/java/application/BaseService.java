package com.user.java.application;

import com.user.java.domain.ModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService<Req, Res, Entity> implements BaseInterface<Req, Res> {

    protected ModelAssembler<Res> modelAssembler;

    @Autowired
    public void setModelAssembler(ModelAssembler<Res> modelAssembler) {
        this.modelAssembler = modelAssembler;
    }

    protected JpaRepository<Entity, Long> baseRepository;

    @Autowired(required = false)
    public void setBaseRepository(JpaRepository<Entity, Long> baseRepository) {
        this.baseRepository = baseRepository;
    }
}
