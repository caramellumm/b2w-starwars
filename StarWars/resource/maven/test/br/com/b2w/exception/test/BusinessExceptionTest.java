package br.com.b2w.exception.test;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.ServiceException;

public class BusinessExceptionTest extends JerseyTest{
	
	@Override
    protected Application configure() {
        return new ResourceConfig(BusinessExceptionTest.class);
    }

	
	@Test
	public void testExcecao() throws ServiceException, BusinessException {
		BusinessException businessException = new BusinessException(new Exception("Erro"));
		assertNotNull(businessException.getCause());
	}
	
	@Test
	public void testExcecaoApenasMensagem() throws ServiceException, BusinessException {
		BusinessException businessException = new BusinessException("Erro");
		assertNotNull(businessException.getMessage());
	}
	
	@Test
	public void testExcecaoComMensagem() throws ServiceException, BusinessException {
		BusinessException businessException = new BusinessException("Erro", new Exception("Erro"));
		assertNotNull(businessException);
	}
	
	@Test
	public void testExcecaoRecuperarMensagem() throws ServiceException, BusinessException {
		String message = new BusinessException("Erro", new Exception("Erro")).getMessage();
		assertNotNull(message);
	}

}
