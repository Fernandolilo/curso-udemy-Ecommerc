package com.systempro.resources.excptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.systempro.services.exceptions.AuthorizationException;
import com.systempro.services.exceptions.DataIntegrityException;
import com.systempro.services.exceptions.FileException;
import com.systempro.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHadler {
	// esta chamando a class StandardError, que foi criada para expor a mensagem
	// necessária
	// dentro deste metodo também é chamado o ObjectNotFoundException.class, pois ha
	// nele mensagem a
	// ser estopada caso haja uma busca por id inexistente.
	// HttpStatus.NOT_FOUND.value(), é o erro 404,
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoud(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	// este é um tratamento de exceptions para tratar deleção de categorias com
	// produtos.
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	// tratamento sintatico, campos com tamanho minimo, maximo e vazio
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	// tratamento de exceção para acesso negado.
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> Autorization(AuthorizationException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	// tratamento de exceção para envio de imagens para o amazonS3
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	// tratamento de exceção para envio de imagens para o AmazonService
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());

		StandardError err = new StandardError(System.currentTimeMillis(), code.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(code).body(err);
	}

	// tratamento de exceção para envio de imagens para o AmazonClientException
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClien(AmazonClientException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	// tratamento de exceção para envio de imagens para o AmazonS3Exception
		@ExceptionHandler(AmazonS3Exception.class)
		public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
			StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Erro Amazon S3", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
}
