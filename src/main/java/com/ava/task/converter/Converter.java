package com.ava.task.converter;

import com.ava.task.dto.AbstractDTO;
import com.ava.task.model.AbstractModel;

public interface Converter<M extends AbstractModel, D extends AbstractDTO> {

	D toDto(M model);
	M fromDto(D dto);
	
}
