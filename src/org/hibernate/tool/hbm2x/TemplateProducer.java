package org.hibernate.tool.hbm2x;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.tool.hbm2x.ArtifactCollector;
import org.hibernate.tool.hbm2x.ExporterException;
import org.hibernate.tool.hbm2x.TemplateHelper;

/**
 * 使用Hibernate Tool 工具，生成POJO文件（支持中文注释）
 * 
 * @author deniro
 * 
 */
public class TemplateProducer {
	private static final Log log = LogFactory.getLog(TemplateProducer.class);
	private final TemplateHelper th;
	private final ArtifactCollector ac;

	public TemplateProducer(TemplateHelper th, ArtifactCollector ac) {
		this.th = th;
		this.ac = ac;
	}

	public void produce(Map additionalContext, String templateName,
			File destination, String identifier, String fileType,
			String rootContext) {
		String tempResult = produceToString(additionalContext, templateName,
				rootContext);

		if (tempResult.trim().length() == 0) {
			log.warn("Generated output is empty. Skipped creation for file "
					+ destination);
			return;
		}
		// FileWriter fileWriter = null;
		Writer fileWriter = null;
		try {
			this.th.ensureExistence(destination);

			this.ac.addFile(destination, fileType);
			log.debug("Writing " + identifier + " to "
					+ destination.getAbsolutePath());
			// fileWriter = new FileWriter(destination);
			fileWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destination), "UTF-8"));
			fileWriter.write(tempResult);
		} catch (Exception e) {
			throw new ExporterException("Error while writing result to file", e);
		} finally {
			if (fileWriter != null)
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					log.warn("Exception while flushing/closing " + destination,
							e);
				}
		}
	}

	private String produceToString(Map additionalContext, String templateName,
			String rootContext) {
		Map contextForFirstPass = additionalContext;
		putInContext(this.th, contextForFirstPass);
		StringWriter tempWriter = new StringWriter();
		BufferedWriter bw = new BufferedWriter(tempWriter);

		this.th.processTemplate(templateName, bw, rootContext);
		removeFromContext(this.th, contextForFirstPass);
		try {
			bw.flush();
		} catch (IOException e) {
			throw new RuntimeException("Error while flushing to string", e);
		}
		return tempWriter.toString();
	}

	private void removeFromContext(TemplateHelper templateHelper, Map context) {
		Iterator iterator = context.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry element = (Map.Entry) iterator.next();
			templateHelper.removeFromContext((String) element.getKey(),
					element.getValue());
		}
	}

	private void putInContext(TemplateHelper templateHelper, Map context) {
		Iterator iterator = context.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry element = (Map.Entry) iterator.next();
			templateHelper.putInContext((String) element.getKey(),
					element.getValue());
		}
	}

	public void produce(Map additionalContext, String templateName,
			File outputFile, String identifier) {
		String fileType = outputFile.getName();
		fileType = fileType.substring(fileType.indexOf('.') + 1);
		produce(additionalContext, templateName, outputFile, identifier,
				fileType, null);
	}

	public void produce(Map additionalContext, String templateName,
			File outputFile, String identifier, String rootContext) {
		String fileType = outputFile.getName();
		fileType = fileType.substring(fileType.indexOf('.') + 1);
		produce(additionalContext, templateName, outputFile, identifier,
				fileType, rootContext);
	}
}