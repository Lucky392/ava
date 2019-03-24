package com.ava.task.factory;

import com.ava.task.converter.Converter;
import com.ava.task.dto.AbstractDTO;
import com.ava.task.exception.AvaException;
import com.ava.task.model.AbstractModel;

public class AvaFactory {

	public static <M extends AbstractModel, D extends AbstractDTO> Converter<M, D> getConverter(Class<? extends Converter<M, D>> clazz) throws AvaException {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new AvaException("Can't create instance!", e);
		}
	}
	
}
