package com.gles.base.exception;

public class JqNullDataExcetpion extends RuntimeException {

	private static final long serialVersionUID = -1037897142748761939L;

	private static String exceMessage = "ObjData3D is null";

	public JqNullDataExcetpion() {
		super(exceMessage);
	}
}
