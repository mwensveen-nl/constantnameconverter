package nl.mwensveen.constantnameconverter;

import java.util.Date;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class ConstantNameConverterHander extends AbstractHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// nothing to do
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Activator.getDefault().getLog().log(new Status(Status.INFO, "cnc", "execute starting" + new Date()));
		try {
			IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (part instanceof ITextEditor) {
				final ITextEditor editor = (ITextEditor) part;
				IDocumentProvider prov = editor.getDocumentProvider();
				IDocument doc = prov.getDocument(editor.getEditorInput());
				ISelection sel = editor.getSelectionProvider().getSelection();

				if (!sel.isEmpty() && sel instanceof TextSelection) {
					final TextSelection textSel = (TextSelection) sel;
					Converter converter = new Converter(textSel.getText());
					String newText = converter.convert();
					if (null != newText) {
						doc.replace(textSel.getOffset(), textSel.getLength(), newText);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// nothing to do
	}

}
