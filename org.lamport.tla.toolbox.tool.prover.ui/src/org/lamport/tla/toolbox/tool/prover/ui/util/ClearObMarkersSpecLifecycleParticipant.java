package org.lamport.tla.toolbox.tool.prover.ui.util;

import org.eclipse.core.runtime.CoreException;
import org.lamport.tla.toolbox.tool.SpecEvent;
import org.lamport.tla.toolbox.tool.SpecLifecycleParticipant;
import org.lamport.tla.toolbox.tool.prover.ui.ProverUIActivator;
import org.lamport.tla.toolbox.tool.prover.ui.view.ObligationsView;
import org.lamport.tla.toolbox.tool.prover.util.ProverHelper;

/**
 * This class listens to changes in specs in order to update information
 * about prover obligations. In particular, it does two things:
 * 
 * 1.) When a spec is closed, it removes all obligation markers on that spec.
 * 
 * 2.) When a spec is opened, it refreshes the obligation view so that if the view
 *     is currently open, it does not continue to display information about
 *     obligations from the previously opened spec.
 *     
 * @author Daniel Ricketts
 *
 */
public class ClearObMarkersSpecLifecycleParticipant extends SpecLifecycleParticipant
{

    public boolean eventOccured(SpecEvent event)
    {
        /*
         * If a spec if being closed remove all obligation markers
         * on that spec. If a spec is being opened, refresh the obligation view.
         */
        if (event.getType() == SpecEvent.TYPE_CLOSE)
        {
            try
            {
                ProverHelper.clearObligationMarkers(event.getSpec().getProject());
            } catch (CoreException e)
            {
                ProverUIActivator.logError("Error removing obligation markers from spec " + event.getSpec().getName()
                        + " when it was closed.", e);
            }
        } else if (event.getType() == SpecEvent.TYPE_OPEN)
        {
            ObligationsView.refreshObligationView();
        }
        return false;
    }
}
