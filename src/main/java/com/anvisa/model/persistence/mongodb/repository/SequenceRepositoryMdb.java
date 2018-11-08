package com.anvisa.model.persistence.mongodb.repository;

import java.math.BigInteger;

import com.anvisa.model.persistence.mongodb.sequence.SequenceException;

public interface SequenceRepositoryMdb  {

	BigInteger getNextSequenceId(String key) throws SequenceException;

}
