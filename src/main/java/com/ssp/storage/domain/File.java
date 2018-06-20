package com.ssp.storage.domain;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class File {

	@Id
	private long id;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "folderId", referencedColumnName = "id")
	private Folder folderId;
	
	private Blob file;
	
	private String absolutePath;
	
	

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Folder getFolderId() {
		return folderId;
	}

	public void setFolderId(Folder folderId) {
		this.folderId = folderId;
	}



	

	public Blob getFile() {
		return file;
	}

	public void setFile(Blob file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", file=" + file + "]";
	}
	
	
	
}
