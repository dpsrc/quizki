package com.haxwell.apps.questions.entities;

/**
 * Copyright 2013,2014 Johnathan E. James - haxwell.org - jj-ccs.com - quizki.com
 *
 * This file is part of Quizki.
 *
 * Quizki is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Quizki is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quizki. If not, see http://www.gnu.org/licenses.
 */

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.AttributeOverride;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.minidev.json.JSONObject;

import com.haxwell.apps.questions.utils.StringUtil;


/**
 * The persistent class for the TOPIC database table.
 * 
 */
@Entity
@Table(name="topic")
public class Topic extends AbstractTextEntity implements EntityWithIDAndTextValuePairBehavior, Serializable {
	private static final long serialVersionUID = 1L;

	//Note: Because tests are not using persistence this annotation is not checked in testing
	@AttributeOverride(name = "text", column = @Column(nullable=false,unique=true))
	
	@ManyToMany(mappedBy="topics")
	Set<Question> questions;

    public Topic() {
    }

	public String getEntityDescription()  {
		return "topic";
	}
	
   public Topic(String str) {
    	this.text = str;
    }

	@Override
	public int hashCode()
	{
		return this.text.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		boolean rtn = (this == o);
		
		if (!rtn && o instanceof Topic)
		{
			Topic that = (Topic)o;
			
			
			rtn = /*(this.id == that.id) && */(StringUtil.equalsCaseInsensitive(this.text, that.text)); 
		}
		
		return rtn;
	}

	@Override
	public String toString()
	{
		return "id: " + this.id + " |text: " + this.text;
	}
	
    public String toJSON() {
    	JSONObject j = new JSONObject();
    	
    	j.put("id", getId());
    	j.put("text", getText());
    	
    	return j.toJSONString();
    }
    
    public Topic fromJSON(JSONObject obj) {
    	this.setId(Long.parseLong(obj.get("id")+""));
    	this.setText(obj.get("text")+"");
    	
    	return this;
    }
}