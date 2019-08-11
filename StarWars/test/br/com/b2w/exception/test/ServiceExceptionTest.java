package br.com.b2w.exception.test;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.ServiceException;

public class ServiceExceptionTest extends JerseyTest{
	
	@Override
    protected Application configure() {
        return new ResourceConfig(ServiceExceptionTest.class);
    }

	@Test
	public void testExcecao() throws ServiceException, BusinessException {
		ServiceException serviceException = new ServiceException(new Exception("Erro"));
		assertNotNull(serviceException.getCause());
	}
	
	@Test
	public void testExcecaoApenasMensagem() throws ServiceException, BusinessException {
		ServiceException serviceException = new ServiceException("Erro");
		assertNotNull(serviceException.getMessage());
	}
	
	@Test
	public void testExcecaoComMensagem() throws ServiceException, BusinessException {
		ServiceException serviceException = new ServiceException("Erro", new Exception("Erro"));
		assertNotNull(serviceException);
	}
	
	@Test
	public void testExcecaoRecuperarMensagem() throws ServiceException, BusinessException {
		String message = new ServiceException("Erro", new Exception("Erro")).getMessage();
		assertNotNull(message);
	}

}
