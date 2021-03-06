/*
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: LargeDocumentDemo2.java,v 1.4 2005/01/29 14:52:57 maartenc Exp $
 */

package org.dom4j.samples;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

/**
 * A test harness to test the content API in DOM4J
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class LargeDocumentDemo2 extends SAXDemo {

    public static void main(String[] args) {
        run(new LargeDocumentDemo2(), args);
    }

    public LargeDocumentDemo2() {
    }

    public void run(String[] args) throws Exception {
        if (args.length < 1) {
            printUsage("<XML document URL>");
            return;
        }

        String xmlFile = args[0];

        Document document = parse(xmlFile);
        process(document);
    }

    protected Document parse(String url) throws Exception {
        SAXReader reader = new SAXReader();

        println("Parsing document:   " + url);

        // enable pruning to call me back as each Element is complete
        reader.addHandler("/PLAY/ACT", new playActHandler());

        println("##### starting parse");
        Document document = reader.read(url);
        println("##### finished parse");

        // the document will be complete but have the prunePath elements pruned
        println("Now lets dump what is left of the document after pruning...");

        return document;
    }

    class playActHandler implements ElementHandler {
        public void onStart(ElementPath path) {
            Element element = path.getCurrent();
            path.addHandler("SCENE/SPEECH", new actSceneSpeechHandler());
        }

        public void onEnd(ElementPath path) {
            Element element = path.getCurrent();
            println("Found Act: " + element.element("TITLE").getText());
            path.removeHandler("SCENE/SPEECH");
            element.detach();
        }
    }

    class actSceneSpeechHandler implements ElementHandler {
        public void onStart(ElementPath path) {
            Element element = path.getCurrent();
            println("Found Start of Speech");
        }

        public void onEnd(ElementPath path) {
            Element element = path.getCurrent();
            println("Found End of Speech by Speaker: "
                    + element.element("SPEAKER").getText());
            element.detach();
        }
    }
}

/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright statements and
 * notices. Redistributions must also contain a copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name "DOM4J" must not be used to endorse or promote products derived
 * from this Software without prior written permission of MetaStuff, Ltd. For
 * written permission, please contact dom4j-info@metastuff.com.
 * 
 * 4. Products derived from this Software may not be called "DOM4J" nor may
 * "DOM4J" appear in their names without prior written permission of MetaStuff,
 * Ltd. DOM4J is a registered trademark of MetaStuff, Ltd.
 * 
 * 5. Due credit should be given to the DOM4J Project - http://www.dom4j.org
 * 
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL METASTUFF, LTD. OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2001-2004 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * $Id: LargeDocumentDemo2.java,v 1.4 2005/01/29 14:52:57 maartenc Exp $
 */
