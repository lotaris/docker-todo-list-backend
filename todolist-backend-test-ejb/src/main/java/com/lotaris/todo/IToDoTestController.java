package com.lotaris.todo;

import com.lotaris.j2ee.itf.TestController;
import javax.ejb.Local;

/**
 * Interface to create the Session Bean to start the integration tests
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Local
public interface IToDoTestController extends TestController {
}
