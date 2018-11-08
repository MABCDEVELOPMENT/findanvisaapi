package com.anvisa.model.persistence.mongodb.sequence;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.anvisa.model.persistence.mongodb.repository.SequenceRepositoryMdb;

@Repository
public class SequenceDaoImpl implements SequenceRepositoryMdb {
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Override
	public BigInteger getNextSequenceId(String key) throws SequenceException {
		// TODO Auto-generated method stub
			
		  //get sequence id
		  Query query = new Query(Criteria.where("_id").is(key));

		  //increase sequence id by 1
		  Update update = new Update();
		  update.inc("seq", 1);

		  //return new increased id
		  FindAndModifyOptions options = new FindAndModifyOptions();
		  options.returnNew(true);

		  //this is the magic happened.
		  SequenceId seqId = 
	            mongoOperation.findAndModify(query, update, options, SequenceId.class);

		  //if no id, throws SequenceException
	          //optional, just a way to tell user when the sequence id is failed to generate.
		  if (seqId == null) {
			throw new SequenceException("Unable to get sequence id for key : " + key);
		  }

		  return seqId.getSeq();

		}
	}


