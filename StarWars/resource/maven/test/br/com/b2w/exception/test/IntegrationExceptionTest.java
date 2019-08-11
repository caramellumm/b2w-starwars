package br.com.b2w.exception.test;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;
import br.com.b2w.exception.ServiceException;

public class IntegrationExceptionTest extends JerseyTest{
	
	@Override
    protected Application configure() {
        return new ResourceConfig(IntegrationExceptionTest.class);
    }

	@Test
	public void testExcecao() throws ServiceException, BusinessException {
		IntegrationException integrationException = new IntegrationException(new Exception("Erro"));
		assertNotNull(integrationException.getCause());
	}
	
	@Test
	public void testExcecaoApenasMensagem() throws ServiceException, BusinessException {
		IntegrationException integrationException = new IntegrationException("Erro");
		assertNotNull(integrationException.getMessage());
	}
	
	@Test
	public void testExcecaoComMensagem() throws ServiceException, BusinessException {
		IntegrationException integrationException = new IntegrationException("Erro", new Exception("Erro"));
		assertNotNull(integrationException);
	}
	
	@Test
	public void testExcecaoRecuperarMensagem() throws ServiceException, BusinessException {
		String message = new IntegrationException("Erro", new Exception("Erro")).getMessage();
		assertNotNull(message);
	}

}
