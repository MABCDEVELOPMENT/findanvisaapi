package com.anvisa.model.persistence.mongodb.repository;

import com.anvisa.model.persistence.mongodb.sequence.SequenceException;

public interface SequenceRepositoryMdb  {

	long getNextSequenceId(String key) throws SequenceException;

}
