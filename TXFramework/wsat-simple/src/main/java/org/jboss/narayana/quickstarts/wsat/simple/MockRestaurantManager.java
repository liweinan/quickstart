/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005-2010, Red Hat, and individual contributors
 * as indicated by the @author tags. 
 * See the copyright.txt in the distribution for a full listing 
 * of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 * 
 * @author JBoss Inc.
 */
/*
 * RestaurantManager.java
 *
 * Copyright (c) 2003 Arjuna Technologies Ltd.
 *
 * $Id: RestaurantManager.java,v 1.3 2004/04/21 13:09:18 jhalliday Exp $
 *
 */
package org.jboss.narayana.quickstarts.wsat.simple;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents the back-end resource for managing Restaurant bookings.
 * 
 * This is a mock implementation that just keeps a counter of how many bookings have been made.
 * 
 * @author paul.robinson@redhat.com, 2012-01-04
 */
public class MockRestaurantManager {

    // The singleton instance of this class.
    private static MockRestaurantManager singletonInstance;

    // A thread safe booking counter
    private AtomicInteger bookings = new AtomicInteger(0);

    /**
     * Accessor to obtain the singleton restaurant manager instance.
     * 
     * @return the singleton RestaurantManager instance.
     */
    public synchronized static MockRestaurantManager getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new MockRestaurantManager();
        }

        return singletonInstance;
    }

    /**
     * Make a booking with the restaurant.
     *
     * @return the iD of the booking
     */
    public synchronized String makeBooking() {
        System.out.println("[SERVICE] makeBooking called on backend resource.");
        return UUID.randomUUID().toString();
    }

    /**
     * Prepare local state changes for the supplied transaction. This method should persist any required information to ensure
     * that it can undo (rollback) or make permanent (commit) the work done inside this transaction, when told to do so.
     * 
     * @param bookingId The transaction identifier
     * @return true on success, false otherwise
     */
    public boolean prepare(String bookingId) {
        System.out.println("[SERVICE] prepare called on backend resource.");
        return true;
    }

    /**
     * commit local state changes for the supplied transaction
     * 
     * @param bookingId The transaction identifier
     */
    public void commit(String bookingId) {
        System.out.println("[SERVICE] commit called on backend resource.");
        bookings.getAndIncrement();
    }

    /**
     * roll back local state changes for the supplied transaction
     * 
     * @param bookingId The transaction identifier
     */
    public void rollback(String bookingId) {
        System.out.println("[SERVICE] rollback called on backend resource.");
    }

    /**
     * Returns the number of bookings
     * 
     * @return the number of bookings.
     */
    public int getBookingCount() {
        return bookings.get();
    }

    /**
     * Reset the booking counter to zero
     */
    public void reset() {
        bookings.set(0);
    }
}
